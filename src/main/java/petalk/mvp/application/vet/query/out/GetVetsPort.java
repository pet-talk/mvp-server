package petalk.mvp.application.vet.query.out;

import petalk.mvp.application.vet.query.GetVetsQueryParameter;
import petalk.mvp.application.vet.response.VetResponse;
import petalk.mvp.core.pagination.Slice;

/**
 * GetVetsPort는 수의사 목록 조회 유스케이스의 포트입니다.
 */
public interface GetVetsPort {
    /**
     * 수의사 목록을 조회합니다.
     * @param request 수의사 목록 조회 쿼리 파라미터
     * @return 수의사 slicing list
     */
    Slice<VetResponse> getVets(GetVetsQueryParameter request);
}
