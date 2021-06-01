package com.bldj.project.listeners

import data.Advert

/**
 * Interface for adding listener to getInfo button.
 */
interface IGetAdvertInfo {
    fun onGetAdvertInfoClicked(ad: Advert)
}