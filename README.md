# TYRONE Project Setup Guide

This is a **greenfield Java project template** designed for building TYRONE.

> Always ensure your environment is properly configured before running the project.

---

## Prerequisites

- **JDK 17** (Required)
- Latest version of **IntelliJ IDEA**
- Basic familiarity with `git`

~~Older JDK versions are acceptable~~  
**Only JDK 17 is supported.**

---

## Setting Up in IntelliJ

### Step-by-Step Instructions

1. **Open IntelliJ**
   - If not on the welcome screen, click `File` → `Close Project`

2. **Open the project**
   - Click `Open`
   - Select the project directory
   - Click `OK`
   - Accept default prompts

3. **Configure the project**
   - Set **Project SDK** to `JDK 17`
   - Set **Project language level** to `SDK default`
   - Refer to the official guide: [IntelliJ SDK Setup](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk)

4. **Run the application**
   - Locate `src/main/java/Tyrone.java`
   - Right-click the file
   - Select `Run Tyrone.main()`

---

## Expected Output

If setup is correct, you should see:
```text
Hello from

ASCII Art truncated for breveity.

What can I do for you?
```

---

## Features

- ✅ Task management (Todo, Deadline, Event)
- ✅ Mark/Unmark tasks as complete
- ✅ Search tasks by keyword
- ✅ Sort deadlines by date
- ✅ Persistent storage
- ✅ JavaFX GUI interface

---

## Commands

| Command | Description | Example |
|---------|-------------|---------|
| `todo <description>` | Add a todo task | `todo read book` |
| `deadline <description> /by <date>` | Add a deadline | `deadline assignment /by 2024-03-15` |
| `event <description> /at <time>` | Add an event | `event meeting /at 2pm` |
| `list` | Show all tasks | `list` |
| `mark <index>` | Mark task as done | `mark 1` |
| `unmark <index>` | Unmark task | `unmark 1` |
| `delete <index>` | Delete a task | `delete 2` |
| `find <keyword>` | Search tasks | `find book` |
| `sort` | Sort deadlines by date | `sort` |
| `bye` | Exit application | `bye` |

---

## Project Structure
```
ip/
├── src/
│   └── main/
│       ├── java/
│       │   └── tyrone/
│       │       ├── Tyrone.java
│       │       ├── Parser/
│       │       ├── exception/
│       │       ├── storage/
│       │       ├── task/
│       │       └── ui/
│       └── resources/
│           └── styles.css
├── data/
│   └── tyrone.txt
└── README.md
```

---

## Troubleshooting

### Issue: "Cannot find JDK 17"
**Solution:** Download and install JDK 17 from [Oracle](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) or [OpenJDK](https://adoptium.net/)

### Issue: "Main class not found"
**Solution:** 
1. Right-click project root → `Mark Directory as` → `Sources Root`
2. Rebuild project: `Build` → `Rebuild Project`

### Issue: Tasks not saving
**Solution:** Ensure `data/` directory exists and has write permissions

---

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature-name`)
3. Commit your changes (follow commit message guidelines)
4. Push to the branch (`git push origin feature-name`)
5. Open a Pull Request

---

## License

This project is licensed under the MIT License.

---

## Author

**Your Name**  
GitHub: [@yishengt](https://github.com/yishengt)

---

## Acknowledgments

- JavaFX for GUI framework
- IntelliJ IDEA for development environment
