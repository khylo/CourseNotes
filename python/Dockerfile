# docker build -t myPython:3.14 . 
# docker run --rm myPython:3.14
# Use the official Python 3.14 slim image as a base
FROM python:3.14-slim AS builder

# Install uv by copying it from the official image
COPY --from=ghcr.io/astral-sh/uv:latest /uv /uvx /bin/

# Set working directory
WORKDIR /app

# Enable bytecode compilation for faster startup
ENV UV_COMPILE_BYTECODE=1
# Ensure uv uses the system Python instead of creating a separate venv
ENV UV_SYSTEM_PYTHON=1

# Install basic and AI libraries using uv
# These are installed to the system site-packages for container isolation
RUN uv pip install \
    numpy \
    pandas \
    click \
    torch \
    transformers \
    scipy

# Final runtime stage (optional, to keep image slim)
FROM python:3.14-slim
WORKDIR /app

# Copy the installed packages from the builder stage
COPY --from=builder /usr/local/lib/python3.14/site-packages /usr/local/lib/python3.14/site-packages
COPY --from=builder /usr/local/bin /usr/local/bin

# Example command to verify installation
CMD ["python", "-c", "import numpy; import pandas; import torch; print('Python 3.14 and AI libs ready!')"]

