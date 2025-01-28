#!/bin/bash

# Static data for the client
PORT=1234        # Replace with your specific port
IP_ADDRESS="127.0.0.1"  # Replace with your server IP address
MESSAGE="HelloServer"
ATTEMPTS=100000  # Number of times to run the client
FAILURES=0    # Counter for failed attempts

for ((i = 1; i <= ATTEMPTS; i++)); do
    OUTPUT=$(./client "$PORT" "$IP_ADDRESS" "$MESSAGE")

    # Check if the output indicates a failure
    if [[ -z "$OUTPUT" || "$OUTPUT" != *"Received:"* ]]; then
        echo "Attempt $i: Failure detected"
        ((FAILURES++))
    else
        echo "Attempt $i: Success"
    fi
done

echo "Total failures: $FAILURES out of $ATTEMPTS attempts."
