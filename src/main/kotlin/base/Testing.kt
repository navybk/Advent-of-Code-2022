package base

import java.lang.IllegalStateException

fun String?.expect(value: String) {
    if (this != value) {
        throw IllegalStateException("value $value do not match expected $this.")
    }
}

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
