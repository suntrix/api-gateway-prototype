rootProject.name = "api-gateway-prototype"

include(
    ":gateway",

    ":targets:default",
    ":targets:open-movie-database",
    ":targets:the-movie-db"
)