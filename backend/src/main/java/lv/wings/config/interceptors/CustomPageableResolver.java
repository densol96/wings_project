package lv.wings.config.interceptors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;

@Component
public class CustomPageableResolver extends PageableHandlerMethodArgumentResolver {

    @Override
    public Pageable resolveArgument(MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {

        // the super method handles @PageableDefault present in the controller method
        Pageable defaultPageable = super.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        /*
         * Default properties set up via @PageableDefault in a controller endpoint!
         * 
         * If default values not provided via @PageableDefault, Spring will handle this:
         * (page = 0, size = 10, Sort = UNSORTED).
         * 
         * Since, the client expects to send page 1, default 0 need to +1.
         */
        Integer defaultPage = defaultPageable.getPageNumber() == 0 ? 1 : defaultPageable.getPageNumber();
        Integer defaultSize = defaultPageable.getPageSize();
        Sort defaultSort = defaultPageable.getSort();

        // Params from the URL that have previously been validated by an interceptor if
        // not null
        String pageParam = webRequest.getParameter("page");
        String sizeParam = webRequest.getParameter("size");
        String sortParam = webRequest.getParameter("sort");
        String directionParam = webRequest.getParameter("direction");
        Sort.Direction directioon = "desc".equals(directionParam) ? Sort.Direction.DESC : Sort.Direction.ASC;

        // Final values that will be used by the controller as Pageable
        Integer finalPage = pageParam != null ? Integer.parseInt(pageParam) : defaultPage;
        Integer finalSize = sizeParam != null ? Integer.parseInt(sizeParam) : defaultSize;
        Sort finalSort = sortParam != null && directionParam != null ? Sort.by(directioon, sortParam)
                : defaultSort;

        // Remember to subtract -1, since spring uses 0 as a starting page
        return PageRequest.of(finalPage - 1, finalSize, finalSort);
    }
}
