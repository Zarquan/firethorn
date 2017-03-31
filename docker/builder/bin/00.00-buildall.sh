#!/bin/bash -eu
# -e: Exit immediately if a command exits with a non-zero status.
# -u: Treat unset variables as an error when substituting.
#
#  Copyright (C) 2017 Royal Observatory, University of Edinburgh, UK
#
#  This program is free software: you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation, either version 3 of the License, or
#  (at your option) any later version.
#
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#
#  You should have received a copy of the GNU General Public License
#  along with this program.  If not, see <http://www.gnu.org/licenses/>.
#
#

# -----------------------------------------------------
# Initialise our path.

    PATH=${PATH}:/builder/bin

# -----------------------------------------------------
# Initialise our paths.

    01.01-init.sh
    
# -----------------------------------------------------
# Checkout a copy of our source code.

    02.01-checkout.sh

# -----------------------------------------------------
# Set the build tag.

    03.01-buildtag.sh

# -----------------------------------------------------
# Update our POM version.

    03.02-versions.sh

# -----------------------------------------------------
# Build our base images.

    04.01-buildbase.sh

#---------------------------------------------------------------------
# Compile our Java code.

    05.01-javamaven.sh

# -----------------------------------------------------
# Build our Java containers.

    05.02-javadocker.sh


