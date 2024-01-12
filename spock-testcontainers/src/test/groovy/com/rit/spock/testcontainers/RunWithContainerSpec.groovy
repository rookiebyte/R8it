package com.rit.spock.testcontainers

import spock.lang.Specification


@RunWithContainer(PostgresSqlContainerProvider)
class RunWithContainerSpec extends Specification {

    def 'load extension with RunWithContainer present, expect no exception'() {
        expect:
        true
    }
}
