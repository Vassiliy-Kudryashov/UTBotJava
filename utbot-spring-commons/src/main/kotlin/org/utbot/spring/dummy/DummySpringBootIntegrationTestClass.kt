package org.utbot.spring.dummy

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper
import org.springframework.test.context.BootstrapWith

@SpringBootTest
@BootstrapWith(SpringBootTestContextBootstrapper::class)
open class DummySpringBootIntegrationTestClass : DummySpringIntegrationTestClass()

@AutoConfigureTestDatabase
class DummySpringBootIntegrationTestClassAutoconfigTestDB : DummySpringBootIntegrationTestClass()
