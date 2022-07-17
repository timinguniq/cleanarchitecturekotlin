package com.example.cleanarchitecturekotlin.features.photos

import com.example.cleanarchitecturekotlin.core.exception.Failure

class PhotoFailure {
    class ListNotAvailable : Failure.FeatureFailure()
    class NonExistentPhoto : Failure.FeatureFailure()
}