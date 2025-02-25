# Multi-Producer Consumer Synchronization ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
 

![Concurrency](https://img.shields.io/badge/Concurrency-MultiThreading-blue)
![Synchronization](https://img.shields.io/badge/Synchronization-Thread--Safe-green)

## 📌 Overview  
This project implements a **Multi-Producer Multi-Consumer** system in Java using a **shared buffer** with proper synchronization techniques. Multiple producers generate data and multiple consumers process it while ensuring **thread safety** and preventing race conditions.


## Flow of Execution

1. **Producers** start producing and adding items to the buffer.
2. If the **buffer is full**, the producer **waits**.
3. **Consumers** start consuming items from the buffer.
4. If the **buffer is empty**, consumers **wait**.
5. Once **all items are produced**, `setProductionComplete()` is called.
6. **Consumers get notified** and, once they consume all items, they **stop**.

## 📂 Project Structure  
```
multi-producer-consumer-sync-java/
│── README.md
│── producerConsumer.java               
```


## 🛠️ Tech Stack  
- **Java (JDK 11+)**
- **Concurrency & Multithreading** (Executors, BlockingQueue, Locks)
- **Synchronization Mechanisms** (Locks, Semaphores, Wait/Notify)



## ⚙️ Installation Guide

## Prerequisites  
- Install **Java JDK 11+**
- Install **Maven/Gradle** (optional, for dependency management)


## 1️⃣ Fork the Repository
- **Click the **Fork** button (top-right corner).**
- **This creates a copy of the repository under your GitHub account.**

## 2️⃣ Clone Your Forked Repository
```sh
git clone https://github.com/your-username/multi-producer-consumer-sync-java.git
cd multi-producer-consumer-sync-java
```
> Replace `your-username` with your actual GitHub username.

## 3️⃣ Create a New Branch (For Your Changes)
```sh
git checkout -b feature-branch
```
> Replace `feature-branch` with a meaningful branch name.

## 4️⃣ Make Changes and Commit
Modify the code, then stage and commit:
```sh
git add .
git commit -m "Description of changes"
```

## 5️⃣ Push Changes to Your Forked Repository
```sh
git push origin feature-branch
```

## 6️⃣ Create a Pull Request (PR)
1. Go to **your forked repository**.
2. Click on **Compare & pull request**.
3. Ensure the **base repository** is the original repo and the **head repository** is your fork.
4. Add a meaningful title and description.
5. Click **Create pull request**.


## 🤝 Contributing  
Contributions are welcome! Feel free to fork this repo and submit a pull request.

## 👥Developers
- `Aakaash M S`
- `Aiyyappan R M`
- `Anna Liza Sibi`



## 📜 License  
This project is licensed under the **MIT License**. Feel free to use and modify it as needed.

---  

