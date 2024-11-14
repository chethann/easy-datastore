import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("com.vanniktech.maven.publish") version "0.30.0"
}

mavenPublishing {
    coordinates(
        groupId = "io.github.chethann",
        artifactId = "easy-datastore",
        version = "1.0.0-alpha1"
    )

    pom {
        name.set("Easy Datastore")
        description.set("A KMP library that gives a easy to use wrapper over Jetpack Datastore")
        inceptionYear.set("2024")
        url.set("https://github.com/chethann/easy-datastore")

        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/license/mit")
            }
        }

        developers {
            developer {
                id.set("chethann")
                name.set("Chethan N")
                email.set("chethann12793@gmail.com")
            }
        }

        scm {
            url.set("https://github.com/chethann/easy-datastore")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)

    signAllPublications()
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
        publishLibraryVariants("debug", "release")
    }


    jvm("desktop")
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "easydatastore"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.androidx.datastore.core.okio)
            implementation(libs.androidx.datastore.preferences.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "io.github.chethann.easy.datastore"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
