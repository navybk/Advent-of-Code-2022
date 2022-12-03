package base

import java.lang.IllegalStateException

fun Int?.expect(value: Int) {
    if (this != value) {
        throw IllegalStateException("value $value do not match expected $this.")
    }
}

fun Long?.expect(value: Long) {
    if (this != value) {
        throw IllegalStateException("value $value do not match expected $this.")
    }
}
