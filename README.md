# TabooLib SDK

## Settings
```groovy
taboolib {
    tabooLibVersion = '5.45'
    loaderVersion = '2.9'
    classifier = null
    // relocate package
    relocate('io.izzel.taboolib', 'ink.ptms.taboolib')
}
```

## Release Source Code
````groovy
processResources {
    from(sourceSets.main.allSource) {
    exclude 'plugin.yml'
}
````