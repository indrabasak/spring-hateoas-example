package com.basaki.hateoas.swagger;

import com.fasterxml.classmate.ResolvedType;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import springfox.documentation.schema.Collections;
import springfox.documentation.schema.Enums;
import springfox.documentation.service.AllowableValues;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ParameterBuilderPlugin;
import springfox.documentation.spi.service.contexts.ParameterContext;
import springfox.documentation.spring.web.DescriptionResolver;
import springfox.documentation.swagger.common.SwaggerPluginSupport;
import springfox.documentation.swagger.schema.ApiModelProperties;

import static com.google.common.base.Strings.isNullOrEmpty;

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
        Optional<ApiParam> apiParam = context.resolvedMethodParameter().findAnnotation(ApiParam.class);
/*        if (!apiParam.isPresent()) {
            apiParam = Optional.fromNullable(AnnotationUtils.findAnnotation(
                    context.methodParameter().getMethod(),
                    ApiParam.class));

            context.parameterBuilder()
                    .allowableValues(allowableValues(
                            context.alternateFor(context.resolvedMethodParameter().getParameterType()),
                            apiParam.transform(toAllowableValue()).or("")));

            if (apiParam.isPresent()) {
                ApiParam annotation = apiParam.get();
                context.parameterBuilder().name(emptyToNull(annotation.name()));
                context.parameterBuilder().description(emptyToNull(descriptions.resolve(annotation.value())));
                context.parameterBuilder().parameterAccess(emptyToNull(annotation.access()));
                context.parameterBuilder().defaultValue(emptyToNull(annotation.defaultValue()));
                context.parameterBuilder().allowMultiple(annotation.allowMultiple());
                context.parameterBuilder().required(annotation.required());
                context.parameterBuilder().hidden(annotation.hidden());
            }
        }*/


    }

    private Function<ApiParam, String> toAllowableValue() {
        return new Function<ApiParam, String>() {
            @Override
            public String apply(ApiParam input) {
                return input.allowableValues();
            }
        };
    }

    private AllowableValues allowableValues(ResolvedType parameterType, String allowableValueString) {
        AllowableValues allowableValues = null;
        if (!isNullOrEmpty(allowableValueString)) {
            allowableValues = ApiModelProperties.allowableValueFromString(allowableValueString);
        } else {
            if (parameterType.getErasedType().isEnum()) {
                allowableValues = Enums.allowableValues(parameterType.getErasedType());
            }
            if (Collections.isContainerType(parameterType)) {
                allowableValues = Enums.allowableValues(Collections.collectionElementType(parameterType).getErasedType());
            }
        }
        return allowableValues;
    }
}
