# Elev8 - Backend

Elev8 is a dynamic digital platform designed to empower students by providing a unified ecosystem for academic collaboration, hackathon exploration, and community learning. This repository contains the source code for the frontend, built with Vite and React.js.

## ✨ Key Features

**Centralized Collaboration Hub**  
A unified platform for students to connect, collaborate on projects, participate in academic discussions, and engage in hackathons.

**Hackathon Team Formation & Event Discovery**  
Students can explore ongoing and upcoming hackathons, and send or receive team formation requests seamlessly within the platform.

**Integrated Coding Profiles**  
Students can link their GitHub, LeetCode, CodeChef, and other profiles to display their coding achievements and technical skills.

**Real-Time Chat**  
WebSocket-powered real-time messaging enables smooth communication in study groups, hackathon teams, college communities, and personal chats.

## ✨ Modules

### 1. Profile Creation
Integration with platforms like GitHub, LeetCode, CodeChef etc. to showcase coding achievements.

### 2. College Section
- **Group Chat**: Real-time messaging with text, emojis.
- **Event Announcements**: Share details of upcoming events.
- **Doubt Solving**: Post and resolve academic doubts.

### 3. Hackathon Section
- **Explore Hackathons**: Browse ongoing, upcoming and past hackathons.
- **Team Formation Requests**: Accept or reject team formation requests with user details.

### 4. Groups & Messages
- **Study Group Chat**: Real-time messaging for academic collaboration within study groups.
- **Personal Chat**: Direct, private messaging between users.
- **Hackathon Chat**: Group chat for hackathon team communication.

## 🚀 Technologies Used

- **Vite**: A next-generation frontend tooling that provides a faster and leaner development experience.
- **React.js**: A JavaScript library for building user interfaces.
- **Tailwind CSS**: A utility-first CSS framework for rapid UI development.

## 📸 Screenshots

**Profile Creation:**
<img width="1920" height="1106" alt="Screenshot 2025-04-25 at 11 15 14 AM" src="https://github.com/user-attachments/assets/051eb053-b37c-4b15-b8b8-2995c20befe2" />

**Profile Creation:**
<img width="1920" height="1106" alt="Screenshot 2025-04-25 at 11 15 24 AM" src="https://github.com/user-attachments/assets/85622137-cebe-4721-a5b8-a23c97d503cd" />

**Hackathon Discovery:**
<img width="1920" height="1106" alt="image" src="https://github.com/user-attachments/assets/ca0127fe-1e1e-4dfa-96ff-7ba2a478d8fd" />

**Hackathon Registration:**
<img width="1920" height="1106" alt="image" src="https://github.com/user-attachments/assets/1de0564e-24bd-4ffc-a1e7-2b11e4b7d438" />

**Hackathon Requests:**
<img width="1920" height="1106" alt="image" src="https://github.com/user-attachments/assets/52e3496c-b7b3-48e3-9354-c388990eb459" />

**Hackathon Requests:**
<img width="1920" height="1106" alt="image" src="https://github.com/user-attachments/assets/537557c2-ac1e-469e-88ed-e7d02f072c0c" />

**College Chat:**
<img width="1920" height="1106" alt="image" src="https://github.com/user-attachments/assets/47568647-15c6-424b-a20b-33cca2c3f7a4" />

**College Community:**
<img width="1920" height="1106" alt="image" src="https://github.com/user-attachments/assets/ef3ff099-792c-4471-84cb-81c737f3c11e" />

**Groups & Messages:**
<img width="1920" height="1106" alt="image" src="https://github.com/user-attachments/assets/673cfcf9-4177-4b9a-b5b6-b437411c283e" />

**Groups & Messages:**
<img width="1920" height="1106" alt="image" src="https://github.com/user-attachments/assets/463f3393-0b4d-4f55-a19b-a13b0e35c413" />

## ⚙️ Getting Started

To get a local copy up and running, follow these simple steps.

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB (local or cloud instance)

### Installation Steps

1. **Clone the repo**

```bash
git clone https://github.com/your_username/elev8-backend.git
```

2. **Navigate to the project directory**

```bash
cd elev8-backend
```

3. **Update the `application.properties` file with your MongoDB connection details.**

4. **Build the project using Maven**

```bash
mvn clean install
```

5. **Run the application**

```bash
mvn spring-boot:run
```

The server will start on the configured port (e.g., `8080`).
