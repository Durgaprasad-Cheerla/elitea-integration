#!/bin/bash

###############################################################################
# ParaBank Test Automation - Execution Script
# Test Case: SCRUM-167 - TC001 User Registration
# Description: Automated test execution script with multiple options
###############################################################################

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Print banner
print_banner() {
    echo -e "${BLUE}"
    echo "╔════════════════════════════════════════════════════════════╗"
    echo "║     ParaBank Test Automation - SCRUM-167                  ║"
    echo "║     TC001: User Registration Test Execution               ║"
    echo "╚════════════════════════════════════════════════════════════╝"
    echo -e "${NC}"
}

# Print colored message
print_message() {
    local color=$1
    local message=$2
    echo -e "${color}${message}${NC}"
}

# Check prerequisites
check_prerequisites() {
    print_message "$YELLOW" "\n🔍 Checking prerequisites..."
    
    # Check Java
    if command -v java &> /dev/null; then
        JAVA_VERSION=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
        print_message "$GREEN" "✅ Java found: $JAVA_VERSION"
    else
        print_message "$RED" "❌ Java not found. Please install Java 11 or higher."
        exit 1
    fi
    
    # Check Maven
    if command -v mvn &> /dev/null; then
        MVN_VERSION=$(mvn -version | head -n 1)
        print_message "$GREEN" "✅ Maven found: $MVN_VERSION"
    else
        print_message "$RED" "❌ Maven not found. Please install Maven 3.6 or higher."
        exit 1
    fi
}

# Install dependencies
install_dependencies() {
    print_message "$YELLOW" "\n📦 Installing dependencies..."
    mvn clean install -DskipTests
    
    if [ $? -eq 0 ]; then
        print_message "$GREEN" "✅ Dependencies installed successfully"
    else
        print_message "$RED" "❌ Failed to install dependencies"
        exit 1
    fi
}

# Run tests
run_tests() {
    local tag=$1
    local browser=$2
    local headless=$3
    
    print_message "$YELLOW" "\n🧪 Running tests..."
    print_message "$BLUE" "   Tag: $tag"
    print_message "$BLUE" "   Browser: $browser"
    print_message "$BLUE" "   Headless: $headless"
    echo ""
    
    # Build Maven command
    MVN_CMD="mvn clean test"
    
    if [ ! -z "$tag" ]; then
        MVN_CMD="$MVN_CMD -Dcucumber.filter.tags=\"$tag\""
    fi
    
    if [ ! -z "$browser" ]; then
        MVN_CMD="$MVN_CMD -Dbrowser=$browser"
    fi
    
    if [ "$headless" = "true" ]; then
        MVN_CMD="$MVN_CMD -Dbrowser.headless=true"
    fi
    
    # Execute tests
    eval $MVN_CMD
    
    TEST_RESULT=$?
    
    if [ $TEST_RESULT -eq 0 ]; then
        print_message "$GREEN" "\n✅ Tests executed successfully!"
    else
        print_message "$RED" "\n❌ Tests failed!"
    fi
    
    return $TEST_RESULT
}

# Open reports
open_reports() {
    print_message "$YELLOW" "\n📊 Opening test reports..."
    
    REPORT_PATH="test-output/cucumber-reports/cucumber-report.html"
    
    if [ -f "$REPORT_PATH" ]; then
        # Detect OS and open report accordingly
        if [[ "$OSTYPE" == "darwin"* ]]; then
            # macOS
            open "$REPORT_PATH"
        elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
            # Linux
            xdg-open "$REPORT_PATH" &> /dev/null || print_message "$YELLOW" "Please open $REPORT_PATH manually"
        elif [[ "$OSTYPE" == "msys" || "$OSTYPE" == "cygwin" ]]; then
            # Windows
            start "$REPORT_PATH"
        fi
        print_message "$GREEN" "✅ Report opened: $REPORT_PATH"
    else
        print_message "$RED" "❌ Report not found: $REPORT_PATH"
    fi
}

# Display menu
display_menu() {
    echo ""
    echo "Select an option:"
    echo "1. Run SCRUM-167 test only (Chrome)"
    echo "2. Run all User Registration tests (Chrome)"
    echo "3. Run tests in Firefox"
    echo "4. Run tests in Edge"
    echo "5. Run tests in Headless mode"
    echo "6. Run High Priority tests"
    echo "7. Install dependencies only"
    echo "8. Open test reports"
    echo "9. Run with custom options"
    echo "0. Exit"
    echo ""
    read -p "Enter your choice [0-9]: " choice
}

# Handle custom options
handle_custom_options() {
    echo ""
    read -p "Enter Cucumber tag (e.g., @SCRUM-167): " custom_tag
    read -p "Enter browser (chrome/firefox/edge): " custom_browser
    read -p "Run in headless mode? (yes/no): " headless_choice
    
    if [ "$headless_choice" = "yes" ]; then
        custom_headless="true"
    else
        custom_headless="false"
    fi
    
    run_tests "$custom_tag" "$custom_browser" "$custom_headless"
}

# Main execution
main() {
    print_banner
    check_prerequisites
    
    if [ $# -eq 0 ]; then
        # Interactive mode
        while true; do
            display_menu
            
            case $choice in
                1)
                    run_tests "@SCRUM-167" "chrome" "false"
                    open_reports
                    ;;
                2)
                    run_tests "@UserRegistration" "chrome" "false"
                    open_reports
                    ;;
                3)
                    run_tests "@SCRUM-167" "firefox" "false"
                    open_reports
                    ;;
                4)
                    run_tests "@SCRUM-167" "edge" "false"
                    open_reports
                    ;;
                5)
                    run_tests "@SCRUM-167" "chrome" "true"
                    open_reports
                    ;;
                6)
                    run_tests "@HighPriority" "chrome" "false"
                    open_reports
                    ;;
                7)
                    install_dependencies
                    ;;
                8)
                    open_reports
                    ;;
                9)
                    handle_custom_options
                    open_reports
                    ;;
                0)
                    print_message "$GREEN" "\n👋 Goodbye!"
                    exit 0
                    ;;
                *)
                    print_message "$RED" "❌ Invalid option. Please try again."
                    ;;
            esac
            
            echo ""
            read -p "Press Enter to continue..."
        done
    else
        # Command-line mode
        TAG="@SCRUM-167"
        BROWSER="chrome"
        HEADLESS="false"
        
        while [[ $# -gt 0 ]]; do
            case $1 in
                --tag)
                    TAG="$2"
                    shift 2
                    ;;
                --browser)
                    BROWSER="$2"
                    shift 2
                    ;;
                --headless)
                    HEADLESS="true"
                    shift
                    ;;
                --install)
                    install_dependencies
                    exit 0
                    ;;
                --report)
                    open_reports
                    exit 0
                    ;;
                *)
                    print_message "$RED" "Unknown option: $1"
                    echo "Usage: $0 [--tag TAG] [--browser BROWSER] [--headless] [--install] [--report]"
                    exit 1
                    ;;
            esac
        done
        
        run_tests "$TAG" "$BROWSER" "$HEADLESS"
        open_reports
    fi
}

# Execute main function
main "$@"
