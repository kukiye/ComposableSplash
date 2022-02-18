package com.xiangxue.composemvvm.splash

class DuplicateIndexException : Exception {
    constructor() : super()
    constructor(message: ISplashComposableTask) : super("${message.index} is duplicate. Class name is ${message.content.toString()}")
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}