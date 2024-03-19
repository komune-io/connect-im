package io.komune.im.commons.http

import io.ktor.client.HttpClient

interface ClientBuilder {
	fun build(): HttpClient
}
