package petalk.mvp.core.acceptance.utils;

import com.google.common.base.CaseFormat;
import jakarta.persistence.*;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

@Profile("test")
@Component
public class DatabaseCleanUp implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    private List<String> tableNames;
    private Set<String> tablesWithGeneratedId;


    @Override
    public void afterPropertiesSet() {
        final Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        tableNames = new ArrayList<>();
        tablesWithGeneratedId = new HashSet<>();

        for (EntityType<?> entity : entities) {
            if (isEntity(entity)) {
                String tableName = getTableName(entity);
                tableNames.add(tableName);

                if (hasGeneratedId(entity)) {
                    tablesWithGeneratedId.add(tableName);
                }
            }
        }
    }

    private boolean hasGeneratedId(EntityType<?> entity) {
        Class<?> javaType = entity.getJavaType();
        for (Field field : javaType.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(GeneratedValue.class)) {
                return true;
            }
        }
        return false;
    }

    private String getTableName(EntityType<?> entity) {
        if (entity.getJavaType().isAnnotationPresent(Table.class)) {
            return entity.getJavaType().getAnnotation(Table.class).name();
        }
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, entity.getName());
    }

    private boolean isEntity(final EntityType<?> e) {
        return null != e.getJavaType().getAnnotation(Entity.class);
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();

        for (final String tableName : tableNames) {
            entityManager.createNativeQuery(String.format("TRUNCATE TABLE %s", tableName)).executeUpdate();

            if (tablesWithGeneratedId.contains(tableName)) {
                entityManager.createNativeQuery(
                                String.format("ALTER TABLE %s ALTER COLUMN %s_id RESTART WITH 1",
                                        tableName,
                                        singularize(tableName)))
                        .executeUpdate();
            }
        }

        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }

    private String singularize(String pluralName) {
        // Special singular forms
        Map<String, String> specialSingular = new HashMap<>();
        specialSingular.put("children", "child");
        specialSingular.put("people", "person");
        specialSingular.put("information", "information");
        specialSingular.put("info", "info");

        if (specialSingular.containsKey(pluralName)) {
            return specialSingular.get(pluralName);
        }

        // Regular singularization rules
        if (pluralName.endsWith("ies") && pluralName.length() > 3) {
            return pluralName.substring(0, pluralName.length() - 3) + "y";
        }
        if (pluralName.endsWith("es") && (pluralName.endsWith("shes") || pluralName.endsWith("ches") || pluralName.endsWith("xes") || pluralName.endsWith("zes") || pluralName.endsWith("oes"))) {
            return pluralName.substring(0, pluralName.length() - 2);
        }
        if (pluralName.endsWith("s")) {
            return pluralName.substring(0, pluralName.length() - 1);
        }
        // Default to return the name as is (for cases that do not fit the above rules)
        return pluralName;
    }
}
