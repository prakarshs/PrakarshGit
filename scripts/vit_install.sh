#!/bin/bash

echo "======================================="
echo "Installing prakarshGit (vit)"
echo "======================================="

INSTALL_DIR="$HOME/.vit"

echo ""
echo "Installing to: $INSTALL_DIR"

# Get script directory (IMPORTANT FIX)
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_ROOT="$(dirname "$SCRIPT_DIR")"

# Check Java
if ! command -v java &> /dev/null
then
    echo "ERROR: Java is not installed."
    exit 1
fi

# Create directory
mkdir -p "$INSTALL_DIR"

# Copy jar (FIXED PATH)
cp "$PROJECT_ROOT/target/vit.jar" "$INSTALL_DIR/vit.jar"

# Create executable wrapper
cat << 'EOF' > "$INSTALL_DIR/vit"
#!/bin/bash
java -jar "$HOME/.vit/vit.jar" "$@"
EOF

chmod +x "$INSTALL_DIR/vit"

# Add to PATH if not already
if [[ ":$PATH:" != *":$INSTALL_DIR:"* ]]; then
    echo "Adding to PATH..."

    if [ -f "$HOME/.bashrc" ]; then
        echo "export PATH=\"\$PATH:$INSTALL_DIR\"" >> "$HOME/.bashrc"
    fi

    if [ -f "$HOME/.zshrc" ]; then
        echo "export PATH=\"\$PATH:$INSTALL_DIR\"" >> "$HOME/.zshrc"
    fi
fi

echo ""
echo "✅ Installation complete!"
echo "Restart terminal or run:"
echo "source ~/.bashrc"
echo ""
echo "Then try:"
echo "vit --help"