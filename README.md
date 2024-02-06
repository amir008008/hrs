
# Hotel Receptionist Support System (HRSS)

The HRSS is a comprehensive tool designed to facilitate the management of guest check-ins, check-outs, and parcel tracking within a hotel environment. Leveraging a microservices architecture with Java 8 and Spring Boot, this system provides an efficient solution for receptionists to ensure guests' parcels are accurately managed throughout their stay.

## Features

- **Guest Management**: Track guest check-ins and check-outs in real time.
- **Parcel Tracking**: Manage parcel reception and collection for hotel guests.
- **Microservices Architecture**: Utilizes Spring Boot for independent service management.
- **Frontend Demo**: A simple HTML interface to demonstrate interaction with the backend.

## Getting Started

### Prerequisites

- Java JDK 8
- Gradle

### Installation

1. **Clone the Project**

    ```sh
    git clone https://github.com/amir008008/hrs.git
    cd hrs
    ```

2. **Build the Project**

    Using Gradle:

    ```bash
    ./gradlew build
    ```


3. **Run the Services**

    - **Guest Management Service**

        ```sh
        ./gradlew :guest-management:bootRun
        ```


    - **Parcel Management Service**

        ```sh
        ./gradlew :parcel-management:bootRun
        ```

        Or using Maven:

        ```bash
        mvn --projects parcel-management spring-boot:run
        ```

### Accessing Swagger UI

Explore the API endpoints through Swagger UI, available at:

- Guest Management: `http://localhost:8081/swagger-ui.html`
- Parcel Management: `http://localhost:8082/swagger-ui.html`

## Frontend Demo

After starting the guest management service, you can interact with the system using the provided `test.html` frontend demo.

1. **Navigate to the `guest-management` service** which serves the static content, including `test.html`, at `http://localhost:8081/test.html`.

2. **Interact with the UI** to perform operations such as checking in a guest, managing parcels, and checking out guests, observing real-time updates through the backend services.

## Development Notes

- The system is configured for simplicity and demonstration purposes. Adjust configurations and CORS policies as necessary for production environments.
- Contributions and feedback are welcome. Please follow the standard GitHub pull request process for contributions.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details.

## Acknowledgments

- Thanks to the Spring Boot team for their excellent framework that simplifies the development of microservices.
- Appreciation to the Java community for continuous support and resources.
