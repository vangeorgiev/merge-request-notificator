package com.mergerequestnotificator.architecture;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

public class LayeredArchitectureTest {

    private static final JavaClasses importedClasses =
            new ClassFileImporter()
                    .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_JARS)
                    .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                    .importPackages("com.mergerequestnotificator");

    @Test
    public void verifyProperLayeredArchitecture() {
        layeredArchitecture()
                .consideringAllDependencies()
                .layer("Schedule").definedBy("com.mergerequestnotificator.schedule..")
                .layer("Core").definedBy("com.mergerequestnotificator.core..")
                .layer("Gateway").definedBy("com.mergerequestnotificator.gateway..")
                .layer("Transport").definedBy("com.mergerequestnotificator.transport..")
                .layer("Application").definedBy("com.mergerequestnotificator.application..")
                .whereLayer("Schedule").mayOnlyBeAccessedByLayers("Application")
                .whereLayer("Core").mayOnlyBeAccessedByLayers("Gateway", "Schedule", "Transport")
                .whereLayer("Gateway").mayNotBeAccessedByAnyLayer()
                .whereLayer("Transport").mayNotBeAccessedByAnyLayer()
                .check(importedClasses);
    }
}
