package com.rit.robusta.util

import com.google.common.base.Charsets
import com.google.common.io.CharStreams
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.TempDir


class FilesSpec extends Specification {

    private static final TEST_TEXT = 'TEST TEXT'

    @TempDir
    @Shared
    File testDirectory

    @Shared
    File testFile


    def setupSpec() {
        def path = testDirectory.getAbsolutePath()
        testFile = new File(path, 'fileName.txt')
        testFile.withWriter { writer -> writer.write(TEST_TEXT) }
    }

    def 'Open file using absolute path, expect correct content'() {
        when:
        def inputStream = Files.getAsStream(testFile.getAbsolutePath())
        then:
        CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8)).strip() == TEST_TEXT
    }

    def 'Open file from resource, expect correct content'() {
        when:
        def inputStream = Files.getAsStream('tool/test_file.txt')
        then:
        CharStreams.toString(new InputStreamReader(inputStream, Charsets.UTF_8)).strip() == results
        where:
        results = 'TEST_TEST_TEST'
    }

    def 'Files::getAsStream with null path, expect FileNotFoundException'() {
        when:
        Files.getAsStream(null)
        then:
        thrown FileNotFoundException
    }
}
