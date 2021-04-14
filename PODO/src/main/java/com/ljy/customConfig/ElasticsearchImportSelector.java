package com.ljy.customConfig;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class ElasticsearchImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		AnnotationAttributes attributes = AnnotationAttributes.fromMap(
				importingClassMetadata.getAnnotationAttributes(EnableElasticsearchInitModule.class.getName(), false));

		boolean init = attributes.getBoolean("init");
		if (init) {
			return new String[] { ElasticSearchInitialConfig.class.getName() };
		} else {
			return new String[] {};
		}
	}

}
