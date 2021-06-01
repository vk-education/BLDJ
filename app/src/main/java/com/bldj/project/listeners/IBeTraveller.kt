package com.bldj.project.listeners

import data.Advert

/**
 * Interface for adding listener to beTraveller button.
 */
interface IBeTraveller {
    fun onBeTravellerClicked(ad: Advert)
}