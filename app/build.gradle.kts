plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.pooespol.pronosticodepartidos"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "com.pooespol.pronosticodepartidos"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.cardview)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(libs.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
}
tasks.register<Javadoc>("generateJavadoc") {
    description = "Genera Javadoc con las referencias del SDK y dependencias."

    // Obtiene los archivos fuente de Java
    val mainSourceSet = android.sourceSets.getByName("main").java.directories
    source(mainSourceSet)

    // Agrega el classpath del SDK de Android y las librerías del proyecto
    val androidComponents = project.extensions.getByType<com.android.build.api.variant.ApplicationAndroidComponentsExtension>()
    (tasks.findByName("compileDebugJavaWithJavac") as? JavaCompile)?.let { compileTask ->
        classpath = files(androidComponents.sdkComponents.bootClasspath, compileTask.classpath)
    }

    // Excluye archivos generados automáticamente que suelen romper la compilación
    exclude("**/R.java", "**/BuildConfig.java", "**/Manifest.java")

    // Opciones de configuración del compilador Javadoc
    (options as StandardJavadocDocletOptions).apply {
        encoding = "UTF-8"
        charSet = "UTF-8"
        isDocFilesSubDirs = true
        // Evita que el proceso falle si encuentra errores menores de sintaxis Javadoc
        addStringOption("Xdoclint:none", "-quiet")
    }

    // Permite que la tarea termine aunque encuentre algún símbolo no resuelto
    isFailOnError = false
}