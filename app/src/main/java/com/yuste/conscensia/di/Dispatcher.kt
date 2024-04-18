@file:Suppress("unused")

package com.yuste.conscensia.di

import javax.inject.Qualifier

annotation class Dispatcher {

    @Qualifier
    annotation class Default

    @Qualifier
    annotation class Io

    @Qualifier
    annotation class Main

}