package com.basaki.hateoas.swagger;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.common.SwaggerPluginSupport;

@SuppressWarnings({"squid:S1854", "squid:S1481", "squid:S1068",
        "squid:UnusedPrivateMethod"})
@Component
@Order(SwaggerPluginSupport.SWAGGER_PLUGIN_ORDER + 1000)
public class CustomApiParamParameterBuilder implements ParameterBuilderPlugin {
    private final DescriptionResolver descriptions;

    @Autowired
    public CustomApiParamParameterBuilder(DescriptionResolver descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public boolean supports(DocumentationType documentationType) {
        return true;
    }

    @Override
    public void apply(ParameterContext context) {
        Optional<ApiParam> apiParam = context.resolvedMethodParameter()
                .findAnnotation(ApiParam.class);
    }

    private Function<ApiParam, String> toAllowableValue() {
        return ApiParam::allowableValues;
    }
}
