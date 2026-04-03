# 🚀 prakarshGit (`vit`)

`vit` is a minimal version control system inspired by Git, built in Java using Maven and Picocli.

It allows you to track files, stage changes, and create commits — all locally, with a simple CLI experience.

---

## ✨ Features

* Initialize repositories
* Stage files
* Commit changes
* View repository status
* Lightweight and fast
* Works from any directory (global CLI)

---

## 📦 Commands

```bash
vit init                # Initialize a new repository
vit add <file>         # Stage a file
vit commit -m "msg"    # Commit staged changes
vit status             # Show repository status
vit --help             # Show help
```

---

## ⚙️ Requirements

* Java 8 or higher
* Maven (for building)

---

## 🛠️ Build

Clone the repository and build the project:

```bash
git clone <your-repo-url>
cd prakarshGit
mvn clean package
```

This will generate:

```
target/vit.jar
```

---

## 💻 Installation (Windows)

### 🔹 Step 1: Run installer

```powershell
install_vit.bat
```

This will:

* Copy `vit.jar` to your user directory
* Create a command wrapper (`vit.cmd`)
* Install `vit` globally

---

### 🔹 Step 2: Restart terminal

Close and reopen PowerShell or CMD.

---

### 🔹 Step 3: Verify installation

```bash
vit --help
```

---

## 📍 Manual PATH Setup (if needed)

If `vit` is not recognized, add this to your PATH:

```
C:\Users\<your-username>\VitGit
```

Then restart your terminal.

---

## 📂 Example Usage

```bash
vit init

echo "Hello World" > file.txt

vit add file.txt
vit commit -m "Initial commit"

vit status
```

---

## 🧠 How It Works (High-Level)

`vit` mimics basic Git concepts:

* Repository metadata stored locally
* Staging area for tracking changes
* Commit snapshots of files

---

## ⚠️ Limitations

This is a learning project and does NOT yet support:

* Remote repositories (push/pull)
* Merge conflicts
* Advanced branching strategies
* Rebasing

---

## 🛣️ Roadmap

Planned improvements:

* Branch support
* Log command
* Diff support
* Remote repository simulation
* Better storage model (Git-like objects)
