package petalk.mvp.application.vet.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import petalk.mvp.application.vet.query.in.GetVetsQuery;
import petalk.mvp.application.vet.query.out.GetVetsPort;

/**
 * GetVetsQueryService는 수의사 목록 조회 유스케이스의 구현체입니다.
 */
@Service
@Transactional(readOnly = true)
public class GetVetsQueryService implements GetVetsQuery {

    private final GetVetsPort getVetsPort;

    public GetVetsQueryService(GetVetsPort getVetsPort) {
        this.getVetsPort = getVetsPort;
    }
    @Override
    public GetVetsQueryResponse getVets(GetVetsQueryRequest request) {
        return new GetVetsQueryResponse(getVetsPort.getVets(request.getParameter()));
    }
}
