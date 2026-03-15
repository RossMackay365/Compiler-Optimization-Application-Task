import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.File
import java.util.*

class Test {

    private fun runTest(fileName: String): List<MutableSet<Int>> {
        val scanner = Scanner(File(fileName))
        val input = readInput(scanner)
        val possible = solve(input)
        return List(input.S) { v -> possible[v] }
    }

    @Test
    fun `simple input`() {
        val expected = listOf(
            mutableSetOf(0, 3),
            mutableSetOf(1, 3),
            mutableSetOf(2, 3),
            mutableSetOf(1, 3),
            mutableSetOf(3)
        )
        val actual = runTest("simple_input.csv")
        assertEquals(expected, actual)
    }

    @Test
    fun `medium input`() {
        val expected = listOf(
            mutableSetOf(0, 1, 2),
            mutableSetOf(1, 2),
            mutableSetOf(1, 2),
            mutableSetOf(1, 2, 3),
            mutableSetOf(1, 2, 5),
            mutableSetOf(1, 2, 5)
        )
        val actual = runTest("medium_input.csv")
        assertEquals(expected, actual)
    }

    @Test
    fun `complex input`() {
        val expected = listOf(
            mutableSetOf(0, 1, 2, 4, 5, 6),
            mutableSetOf(1, 2, 4, 5, 6),
            mutableSetOf(2, 4, 5, 6),
            mutableSetOf(1, 2, 3, 4, 5, 6),
            mutableSetOf(1, 2, 4, 5, 6),
            mutableSetOf(1, 2, 4, 5, 6),
            mutableSetOf(1, 2, 4, 6),
            mutableSetOf(1, 2, 4, 6),
            mutableSetOf(1, 2, 4, 5, 6)
        )
        val actual = runTest("complex_input.csv")
        assertEquals(expected, actual)
    }

    @Test
    fun `limited cargo types`() {
        val expected = listOf(
            mutableSetOf(1, 2),
            mutableSetOf(1, 2),
            mutableSetOf(2),
            mutableSetOf(3),
            mutableSetOf(1, 2),
            mutableSetOf(1, 2),
            mutableSetOf(2, 3),
            mutableSetOf(1, 3)
        )
        val actual = runTest("limited_cargo_types_input.csv")
        assertEquals(expected, actual)
    }
}