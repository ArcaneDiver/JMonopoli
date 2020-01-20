#!/bin/bash

git ls-files src/ | xargs wc -l | tail -1

exit 0
