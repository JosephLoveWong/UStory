## 简介 ##
一、前世今生

Kotlin是由JetBrains开发的针对JVM、Android和浏览器的静态编程语言，目前，在Apache组织的许可下已经开源。

Hello World
Posted on July 19, 2011 by Dmitry Jemerov
Today at the JVM Language Summit, JetBrains is unveiling the new project we’ve been working on for almost a year now. The project is Kotlin, a new statically typed programming language for the JVM.


Google I/O 2017
Google makes Kotlin a first-class language for writing Android apps


TIOBE 2018.8 43	Kotlin

二、理念

Kotlin 的目标是“创建一种 与 Java 100% 兼容，但比 Java 更安全、更简洁、更灵活，且不会过于复杂的语言”。


- 兼容性：Kotlin与JDK完全兼容，确保Kotlin应用程式可以在较旧的Android设备上运行而无任何问题。Kotlin 工具在Android Studio 中会完全支持，并且兼容Android构建系统。

- 性能：由于非常相似的位元组码结构，Kotlin 应用程式的运行速度与Java 类似。随着Kotlin 对内联函数的支持，使用lambda表达式的代码通常比用Java 写的代码运行得更快。

- 相互操作性：Kotlin可与Java进行100％的相互操作，允许在Kotlin应用程式中使用所有现有的Android类别库。

- 占用：Kotlin 具有非常紧凑的执行时函式库，可以使用ProGuard进一步减少。在实际应用程式中，几百个方法以及apk文件占用不到100K的大小。

- 编译时长：Kotlin 支持高效的增量编译，增量构建通常与Java一样快或者更快。

- 学习曲线：对于Java开发人员而言，Kotlin入门容易。Kotlin Koans 透过一系列互动练习提供了主要功能的指南。

三、特色

- 语法简洁
Kotlin提供了大量的语法糖（有函数声明，类的创建，集合相关，范围运算符等等大量简洁的语法）、 Lambda表达式（Java8支持），简洁的函数表示法。并吸收了其他语言的优点：模板字符串，运算符重载，方法扩展，命名参数等。

- 安全性
Kotlin提供了安全符“？”，当变量可以为null时，必须使用可空安全符？进行声明，否则会出现编译错误。并且，Kotlin还提供了智能的类型判断功能，使用is类型判断后，编译器自动进行类型转换。

- 完全兼容Java
Kotlin的另一个优势就是可以100%的兼容Java，Kotlin和Java之间可以相互调用。使用Kotlin进行Android或者Java服务端开发，可以导入任意的Java库。

- IDE工具支持
在Google官方发布的最新版本的Android Studio 3.0上，已经默认集成了Kotlin，对于一些老版本，也可以通过插件的方式来集成Kotlin。所以，使用JetBrains提供的IDE，可以为Kotlin开发提供最佳的环境支持。就像JetBrains所说：一门语言需要工具化，而在 JetBrains，这正是我们做得最好的地方！
在IDE中可以一键转换Java代码为Kotlin代码，同时Kotlin代码也可以反编译成Java代码。

四、跨平台开发

- Kotlin 用于服务器端
Kotlin 非常适合开发服务器端应用程序，允许编写简明且表现力强的代码， 同时保持与现有基于 Java 的技术栈的完全兼容性以及平滑的学习曲线

- Kotlin 用于 Android
Kotlin 非常适合开发 Android 应用程序，将现代语言的所有优势带入 Android 平台而不会引入任何新的限制

- Kotlin 用于 JavaScript
Kotlin 提供了 JavaScript 作为目标平台的能力。它通过将 Kotlin 转换为 JavaScript 来实现。目前的实现目标是 ECMAScript 5.1，但也有最终目标为 ECMAScript 2015 的计划

- Kotlin/Native
Kotlin/Native 是一种将 Kotlin 编译为没有任何虚拟机的原生二进制文件的技术。 它包含基于 LLVM 的 Kotlin 编译器后端以及 Kotlin 运行时库的原生实现。Kotlin/Native 主要为允许在不希望或不可能使用虚拟机的平台（如 iOS、嵌入式领域等）编译、 或者开发人员需要生成不需要额外运行时的合理大小的独立程序而设计的。（还在开发中）



参考资料

https://kotlinlang.org/

https://www.kotlincn.net/

https://blog.jetbrains.com/kotlin