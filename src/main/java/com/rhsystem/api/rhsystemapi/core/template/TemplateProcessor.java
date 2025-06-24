package com.rhsystem.api.rhsystemapi.core.template;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

/**
 * The TemplateProcessor class is responsible for processing templates with dynamic values.
 * It uses a {@code TemplateEngine} to merge the provided template with a map of dynamic values.
 * This enables generating content dynamically by resolving placeholders in the template with the provided data.
 * <p>
 * This class is typically used in contexts where template rendering is needed, such as email generation
 * or other dynamic document creation scenarios.
 * <p>
 * Dependencies:
 * - {@code TemplateEngine}: Handles the actual processing/rendering of templates.
 */
@Component
public class TemplateProcessor {
    private final TemplateEngine templateEngine;

    /**
     * Constructs a new TemplateProcessor with the specified TemplateEngine.
     *
     * @param templateEngine the TemplateEngine instance used for processing templates
     */
    public TemplateProcessor(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    /**
     * Processes the given template by merging it with the provided dynamic values.
     * The method resolves placeholders in the template using the key-value pairs
     * provided in the {@code values} map and returns the rendered content as a string.
     *
     * @param template the template to be processed, containing placeholders to be replaced
     * @param values   a map containing the dynamic values used to replace placeholders in the template
     *
     * @return the rendered template content as a string, with placeholders resolved using the provided values
     */
    public String process(String template, Map<String, Object> values) {
        Context context = new Context();
        context.setVariables(values);
        return this.templateEngine.process(template, context);
    }

}
