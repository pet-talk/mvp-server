package petalk.mvp.application.vet.query.in;

import petalk.mvp.application.vet.query.GetVetsQueryParameter;
import petalk.mvp.application.vet.query.validator.GetVetsRequestValidator;
import petalk.mvp.application.vet.response.VetResponse;
import petalk.mvp.core.pagination.Slice;

/**
 * GetVetsUsecase는 수의사 목록을 조회하는 유스케이스입니다.
 */
public interface GetVetsQuery {
    GetVetsQueryResponse getVets(GetVetsQueryRequest request);

    public class GetVetsQueryRequest {
        private final GetVetsQueryParameter parameter;

        private GetVetsQueryRequest(GetVetsQueryParameter parameter) {
            this.parameter = parameter;
        }

        public static GetVetsQueryRequest of(GetVetsQueryParameter parameter, GetVetsRequestValidator validator) {
            GetVetsQueryRequest getVetsQueryRequest = new GetVetsQueryRequest(parameter);
            validator.validate(getVetsQueryRequest);
            return getVetsQueryRequest;
        }
        public GetVetsQueryParameter getParameter() {
            return parameter;
        }
    }

    public class GetVetsQueryResponse {

        private final Slice<VetResponse> vets;

        public GetVetsQueryResponse(Slice<VetResponse> vets) {
            this.vets = vets;
        }

        public Slice<VetResponse> getVets() {
            return vets;
        }
    }
}
