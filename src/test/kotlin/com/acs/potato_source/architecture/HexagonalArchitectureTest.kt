package com.acs.potato_source.architecture

import com.tngtech.archunit.junit.AnalyzeClasses
import com.tngtech.archunit.junit.ArchTest
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses

@AnalyzeClasses(packages = ["com.acs.potato_source"])
class HexagonalArchitectureTest {

    @ArchTest
    val domainShouldNotDependOnOutside = noClasses()
        .that().resideInAPackage("..domain..")
        .should().dependOnClassesThat().resideInAnyPackage(
            "..application..",
            "..infrastructure..",
            "org.springframework..",
            "org.springframework.data.mongodb..",
            "org.springframework.data.elasticsearch.."
        )
        .because("Domain must be pure and agnostic of frameworks and external layers.")

    @ArchTest
    val applicationShouldNotDependOnInfrastructure = noClasses()
        .that().resideInAPackage("..application..")
        .should().dependOnClassesThat().resideInAPackage("..infrastructure..")
        .because("Application layer should only interact with Infrastructure via Domain Ports (interfaces).")

}