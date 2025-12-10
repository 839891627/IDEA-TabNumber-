plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.arvin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    // 使用较低版本以确保向后兼容性
    version.set("2023.3.8")
    // 使用 IC (Community) 版本以支持更广泛的用户群体
    // 同时确保与 Ultimate 版本兼容
    type.set("IC")
    // 配置插件依赖，使用可选依赖以支持不同 IDE
    plugins.set(
        listOf(
            "java"  // Java 支持 - 在社区版和专业版中都可用
            // PHP 插件将通过 plugin.xml 中的可选依赖处理
        )
    )
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    patchPluginXml {
        // 支持范围：自 2023.3 起的 IntelliJ 平台版本
        sinceBuild.set("233")
        untilBuild.set("999.*") // 暂不设置上限，跟随平台更新

        changeNotes.set("""
            <h3>Version 1.0</h3>
            <ul>
                <li>为编辑器标签添加数字前缀，便于快速定位</li>
                <li>提供 1-9 快捷动作，配合 IdeaVim 等实现数字跳转</li>
                <li>监听标签变化并自动刷新编号显示</li>
                <li>适用于 IntelliJ IDEA Community/Ultimate 2023.3 及以上</li>
            </ul>
        """.trimIndent())
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
