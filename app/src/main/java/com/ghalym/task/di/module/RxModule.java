package com.ghalym.task.di.module;


import com.ghalym.task.network.RxSingleSchedulers;

import dagger.Module;
import dagger.Provides;

@Module
public class RxModule {
    @Provides
    public RxSingleSchedulers providesScheduler() {
        return RxSingleSchedulers.Companion.getDEFAULT();
    }
}
