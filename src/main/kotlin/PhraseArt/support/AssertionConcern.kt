package PhraseArt.support

open class AssertionConcern {
    protected fun assertArgumentEquals(anObject1: Any, anObject2: Any, aMessage: String) {
        if (anObject1 != anObject2) {
            throw IllegalArgumentException(aMessage)
        }
    }

    protected fun assertArgumentFalse(aBoolean: Boolean, aMessage: String) {
        if (aBoolean) {
            throw IllegalArgumentException(aMessage)
        }
    }

    protected fun assertArgumentLength(aString: String?, aMaximum: Int, aMessage: String) {
        if (aString == null)
            return

        val length = aString.trim { it <= ' ' }.length
        if (length > aMaximum) {
            throw IllegalArgumentException(aMessage)
        }
    }

    protected fun assertArgumentLength(aString: String?, aMinimum: Int, aMaximum: Int, aMessage: String) {
        if (aString == null)
            return

        val length = aString.trim { it <= ' ' }.length
        if (length < aMinimum || length > aMaximum) {
            throw IllegalArgumentException(aMessage)
        }
    }

    protected fun assertArgumentNotEmpty(aString: String?, aMessage: String) {
        if (aString == null || aString.trim { it <= ' ' }.isEmpty()) {
            throw IllegalArgumentException(aMessage)
        }
    }

    protected fun assertArgumentNotEquals(anObject1: Any, anObject2: Any, aMessage: String) {
        if (anObject1 == anObject2) {
            throw IllegalArgumentException(aMessage)
        }
    }

    protected fun assertArgumentNull(anObject: Any?, aMessage: String) {
        if (anObject != null) {
            throw IllegalArgumentException(aMessage)
        }
    }

    protected fun assertArgumentNotNull(anObject: Any?, aMessage: String) {
        if (anObject == null) {
            throw IllegalArgumentException(aMessage)
        }
    }

    protected fun assertRegularExpression(aString: String, regex: Regex, aMessage: String) {
        if (!regex.matches(aString)) {
            throw IllegalArgumentException(aMessage)
        }
    }

    protected fun assertArgumentRange(aValue: Double, aMinimum: Double, aMaximum: Double, aMessage: String) {
        if (aValue < aMinimum || aValue > aMaximum) {
            throw IllegalArgumentException(aMessage)
        }
    }

    protected fun assertArgumentRange(aValue: Float, aMinimum: Float, aMaximum: Float, aMessage: String) {
        if (aValue < aMinimum || aValue > aMaximum) {
            throw IllegalArgumentException(aMessage)
        }
    }

    protected fun assertArgumentRange(aValue: Int, aMinimum: Int, aMaximum: Int, aMessage: String) {
        if (aValue < aMinimum || aValue > aMaximum) {
            throw IllegalArgumentException(aMessage)
        }
    }

    protected fun assertArgumentRange(aValue: Long, aMinimum: Long, aMaximum: Long, aMessage: String) {
        if (aValue < aMinimum || aValue > aMaximum) {
            throw IllegalArgumentException(aMessage)
        }
    }

    protected fun assertArgumentTrue(aBoolean: Boolean, aMessage: String) {
        if (!aBoolean) {
            throw IllegalArgumentException(aMessage)
        }
    }

    protected fun assertStateFalse(aBoolean: Boolean, aMessage: String) {
        if (aBoolean) {
            throw IllegalStateException(aMessage)
        }
    }

    protected fun assertStateTrue(aBoolean: Boolean, aMessage: String) {
        if (!aBoolean) {
            throw IllegalStateException(aMessage)
        }
    }
}