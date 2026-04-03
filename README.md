# vit — a minimal version control system

A lightweight, Git-inspired version control system built from scratch in Java.

<p align="center">
  <!-- 🎥 Demo GIF (add later) -->
  <img src="docs/demo.gif" alt="vit demo" width="700"/>
</p>

---

## Overview

`vit` is a minimal, local-first version control system designed to explore the core ideas behind tools like Git — staging, snapshots, and repository state — without the complexity of distributed systems.

It is built using Java and picocli, and packaged as a global CLI that works from any directory.

---

## Features

* Initialize repositories
* Stage files for commit
* Create commit snapshots
* Inspect working directory state
* View commit history (`vit log`)
* Simple, predictable CLI interface

---

## Installation

### Prerequisites

* Java 8 or higher

---

### Windows

```powershell
scripts\install_vit.bat
```

Restart your terminal, then verify:

```bash
vit --help
```

---

### Linux / WSL

```bash
chmod +x scripts/install.sh
./scripts/install.sh
```

Reload your shell:

```bash
source ~/.bashrc
```

Verify:

```bash
vit --help
```

---

## Quick Start

```bash
vit init

echo "hello world" > file.txt

vit add file.txt
vit commit -m "initial commit"

vit status
vit log
```

---

## Command Reference

| Command          | Description             |
| ---------------- | ----------------------- |
| `vit init`       | Initialize a repository |
| `vit add <file>` | Stage a file            |
| `vit commit -m`  | Create a commit         |
| `vit status`     | Show working tree state |
| `vit log`        | Show commit history     |
| `vit --help`     | Show CLI help           |

---

## Architecture (High-Level)

`vit` models a simplified version of Git’s core workflow:

* **Working Directory** — files on disk
* **Staging Area** — tracked changes
* **Commits** — snapshots of state

The system is intentionally minimal to focus on core concepts rather than edge cases.

---

## Limitations

This project is intentionally scoped and does not include:

* Remote repositories (push / pull)
* Branching and merging
* Conflict resolution
* Rebasing or history rewriting

---

## Roadmap

* [ ] Branch support
* [ ] Commit history (`vit log`)
* [ ] File diffs
* [ ] Git-like object storage (blobs / trees)
* [ ] Remote repository simulation

---

## Building from Source

```bash
git clone https://github.com/prakarshs/PrakarshGit.git
cd prakarshGit
mvn clean package
```

Output:

```
target/vit.jar
```

---
