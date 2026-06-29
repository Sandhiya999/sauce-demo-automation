Sauce Demo – Selenium Automation Framework

> End-to-End Test Automation Framework built with **Selenium WebDriver + Java + TestNG + Page Object Model**  
> Automating the complete E-Commerce flow of [SauceDemo](https://www.saucedemo.com/)

---

## 🛠️ Tech Stack

| Tool | Purpose |
|------|---------|
| Selenium WebDriver | Browser automation |
| Java | Programming language |
| TestNG | Test execution & management |
| Page Object Model (POM) | Framework design pattern |
| Extent Reports | HTML test reporting |
| Maven | Build & dependency management |
| GitHub Actions | CI/CD pipeline |
| Jenkins | CI/CD pipeline (local) |
| GitHub Actions | CI/CD pipeline (cloud) |
| Log4j | Logging |

---

## 📁 Project Structure

```
sauce-demo-automation/
├── .github/workflows/        # CI/CD - GitHub Actions pipeline
├── src/
│   ├── main/java/
│   │   └── pages/            # Page Object classes (one class per page)
│   └── test/java/
│       ├── tests/            # Test classes
│       └── utilities/        # Base class, listeners, retry, data providers
├── reports/                  # Extent HTML Reports
├── logs/                     # Log4j execution logs
├── testng.xml                # TestNG suite - sequential & parallel execution
└── pom.xml                   # Maven dependencies
```

---

## ✅ Test Coverage

### 1️⃣ End-to-End Test (Single Flow)
Complete user journey from Login → Product Selection → Cart → Checkout → Order Confirmation

### 2️⃣ Individual Feature Tests (Incremental Flow)

| # | Test Scenario |
|---|--------------|
| 1 | Login with **valid & invalid credentials** (DataProvider) → Logout |
| 2 | Login → Add items to cart → **Verify cart count** → Logout |
| 3 | Login → Add to cart → **View cart** → Logout |
| 4 | Login → Add to cart → View cart → **Verify cart items** → Logout |
| 5 | Login → Add to cart → View cart → Verify items → **Add/Remove from cart** → Logout |
| 6 | Login → Add to cart → View cart → Verify items → Add/Remove → **Proceed to Checkout** → Logout |
| 7 | Login → Add to cart → View cart → Verify items → Add/Remove → Checkout → **Fill details with valid & invalid data** (DataProvider) → Logout |
| 8 | Login → Add to cart → View cart → Verify items → Add/Remove → Checkout → Fill details → **Submit & Place Order** → Logout |

---

## 🔑 Key Framework Features

### Page Object Model with Page Chaining
- Dedicated class for each web page
- Page chaining implemented by returning page objects from methods — enables clean, fluent test writing

### TestNG Advanced Features
- **DataProvider** — data-driven testing for login & checkout scenarios
- **Groups** — logical grouping of test cases (smoke, regression)
- **Parameters** — browser & environment configuration via testng.xml
- **ITestListener** — custom listener for real-time test status tracking
- **RetryAnalyzer** — automatic retry on flaky test failures
- **Sequential & Parallel Execution** — controlled via testng.xml

### Reporting & Logging
- **Extent Reports** — detailed HTML reports with pass/fail/skip status and screenshots
- **Log4j** — execution logs for debugging and traceability

### CI/CD
- **GitHub Actions** — automated test 
  execution on every code push (cloud)
- **Jenkins** — pipeline configuration 
  for local CI/CD execution (Jenkinsfile)
---

## ▶️ How to Run

### Prerequisites
- Java 11+
- Maven
- Chrome Browser

### Run all tests
```bash
mvn clean test
```

### Run specific group
```bash
mvn clean test -Dgroups=smoke
```

### Run via TestNG XML
```bash
mvn test -DsuiteXmlFile=testng.xml
```

---

## 📊 Test Reports

After execution, Extent HTML reports are generated under:
```
reports/ExtentReport.html
```
Open in browser to view detailed results with logs and status.

---

## 👩‍💻 Author

**Sandhiya Devi** — QA Engineer with 5+ years in testing  
Transitioning from Manual to Automation Testing  
[LinkedIn](https://www.linkedin.com/in/) | [GitHub](https://github.com/Sandhiya999)

---
