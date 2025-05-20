# 📥 Customer CSV Upload & Batch Processing System

A modern Spring Boot application that allows users to upload customer data via a CSV file. The system processes the file using **Spring Batch**, inserts records into a database, and **intelligently prevents duplicate uploads** using **SHA-256 hashing**. Each upload is tracked in a separate table for full traceability and audit purposes.

---

## 🚀 Key Features

- ✨ **CSV Upload with Validation**
- 🧠 **Duplicate Detection via SHA-256 Hashing**
- ⚙️ **Trigger Spring Batch Job on Upload**
- 📁 **Store Files in `/uploads` Folder**
- 🗃 **Persist Customer Records to Database**
- 📂 **Track File Metadata in `uploaded_file` Table**
- 🌐 **Test Easily Using Swagger UI**

---

## 📦 Tech Stack

- ☕ Java 17  
- 🌱 Spring Boot  
- 🧩 Spring Batch  
- 🗃 Spring Data JPA  
- 🧪 Swagger (SpringDoc OpenAPI)  
- 🧰 Maven  
- 🛢️ MySQL

---

## 🗂️ Project Structure

<img width="328" alt="image" src="https://github.com/user-attachments/assets/bbbbfeec-5e2a-4139-a060-50bb2bdf5d2c" />

---

## 📂 CSV Format Example

```csv
id,firstName,lastName,email,gender,contactNo,country,dob
1,John,Doe,john@example.com,Male,1234567890,USA,1990-01-01
2,Jane,Smith,jane@example.com,Female,0987654321,UK,1992-05-12


🧪 Workflow

📤 User uploads a CSV file via Swagger or Postman
🔐 SHA-256 hash is generated
🔍 System checks if file already exists in uploaded_file table
✅ If new → file saved, batch job starts, and file info recorded
❌ If duplicate → request blocked with 409 status


# ⚙️ How to Run Locally

# Clone the project
git clone https://github.com/aadilnoor/customer-batch-processing.git

# Build the project
mvn clean install

# Run the app
mvn spring-boot:run

🙌 Author
👨‍💻 Aadil Noor
