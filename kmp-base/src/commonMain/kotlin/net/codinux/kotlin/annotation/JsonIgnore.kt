package net.codinux.kotlin.annotation

/**
 * Annotation to be able to apply Jackson's @com.fasterxml.jackson.annotation.JsonIgnore in common module
 */
// match the target and retention settings of Jackson's JsonIgnore annotation
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION, AnnotationTarget.CONSTRUCTOR, AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
expect annotation class JsonIgnore()