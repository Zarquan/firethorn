#!/bin/bash -eu
# -e: Exit immediately if a command exits with a non-zero status.
# -u: Treat unset variables as an error when substituting.
#
#  Copyright (C) 2013 Royal Observatory, University of Edinburgh, UK
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

homedir="${HOME:?}"
setupdir="${HOME:?}/setup"

touch "${HOME:?}/tap_service"
>tap_service
chcon -t svirt_sandbox_file_t "${HOME:?}/tap_service" 

chmod a+r "${HOME:?}/tap_service"

chcon -t svirt_sandbox_file_t "${setupdir:?}/apache-tap-config-script.sh" 
chmod a+r "${setupdir:?}/apache-tap-config-script.sh"

gillianip=$(docker inspect --format '{{ .NetworkSettings.IPAddress }}' gillian)

docker run -p 80:80 --name firepache  --memory 512M --volume "${setupdir:?}/apache-tap-config-script.sh:${setupdir:?}/apache-tap-config-script.sh" \--env "gillianip=${gillianip:?}" -d firethorn/apache 

docker exec  firepache /bin/sh -l -c ${setupdir:?}/apache-tap-config-script.sh




