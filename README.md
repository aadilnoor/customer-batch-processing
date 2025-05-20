📥 Customer CSV Upload & Batch Processing System

A modern Spring Boot application that allows users to upload customer data via CSV file. The system processes the file using Spring Batch, inserts records into the database, and intelligently prevents duplicate file uploads using SHA-256 hashing. Uploaded file details are also stored in a separate tracking table for full traceability.

🚀 Key Features

✨ CSV Upload with Validation  
🧠 Smart Duplicate Detection using SHA-256  
⚙️ Spring Batch Job Trigger on Upload  
📁 File Saved in Local `/uploads` Folder  
📊 Customer Data Stored in DB  
📂 File Info Saved in a Separate Table (`uploaded_file`)  
🌐 Swagger UI for Easy Testing & Docs  

📦 Tech Stack

- ☕ Java 17  
- 🌱 Spring Boot  
- 🧩 Spring Batch  
- 🗃 Spring Data JPA  
- 🧪 Swagger (SpringDoc OpenAPI)  
- 🧰 Maven  
- 🛢️ MySQL  

📁 Project Modules

src/
├── controller/            # API layer (CSV Upload endpoint)
├── config/                # Spring Batch configuration
├── model/                 # Entities: Customer, UploadedFile
├── repository/            # JPA repositories
├── utils/                 # File hashing helper
└── resources/
    └── application.yml    # App configuration


📂 CSV Format Example

id,firstName,lastName,email,gender,contactNo,country,dob
1,John,Doe,john@example.com,Male,1234567890,USA,1990-01-01
2,Jane,Smith,jane@example.com,Female,0987654321,UK,1992-05-12


🧪 Workflow

📤 User uploads a CSV file via Swagger or Postman
🔐 SHA-256 hash is generated
🔍 System checks if file already exists in uploaded_file table
✅ If new → file saved, batch job starts, and file info recorded
❌ If duplicate → request blocked with 409 status


⚙️ How to Run Locally

# Clone the project
git clone https://github.com/aadilnoor/customer-batch-processing.git

# Build the project
mvn clean install

# Run the app
mvn spring-boot:run
