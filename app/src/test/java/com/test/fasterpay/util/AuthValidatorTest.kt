package com.test.fasterpay.util

import com.test.fasterpay.util.AuthValidator.isValidPassword
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AuthValidatorTest {
    //only numbers => false
    @Test
    fun validPassword_onlyNumber_false() {
        assertFalse(isValidPassword("11223344"))
    }
    //only lowercase letters => false
    @Test
    fun validPassword_onlyLowercase_false() {
        assertFalse(isValidPassword("aabbccdd"))
    }
    //only uppercase letters => false
    @Test
    fun validPassword_onlyUppercase_false() {
        assertFalse(isValidPassword("AABBCCDD"))
    }
    //all lowercase letters and numbers => false
    @Test
    fun validPassword_allLowerAndNumbers_false() {
        assertFalse(isValidPassword("abcdefgh12"))
    }
    //all uppercase letters and numbers => false
    @Test
    fun validPassword_allUpperAndNumbers_false() {
        assertFalse(isValidPassword("ABCDEFGH12"))
    }
    //uppercase + lowercase letters without numbers => false
    @Test
    fun validPassword_UpperAndLower_false() {
        assertFalse(isValidPassword("ABCDEFGHabcdefgh"))
    }
    //uppercase + lowercase letters with symbols => false
    @Test
    fun validPassword_UpperLowerAndrSymbols_false() {
        assertFalse(isValidPassword("ABCHabc!@#"))
    }
    //uppercase + lowercase letters + number with less than 8 characters length
    @Test
    fun validPassword_lessThan8_false() {
        assertFalse(isValidPassword("Test123"))
    }
    // valid passwords
    @Test
    fun validPassword_ValidPasswords_false() {
        assertTrue(isValidPassword("Test1234"))
        assertTrue(isValidPassword("Aa12345656"))
        assertTrue(isValidPassword("12345678Aa"))
        assertTrue(isValidPassword("1234Aa5678"))
    }
}