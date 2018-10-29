# treblewallet-bitbox-client
Java client library to connect to the Bitbox USB HSM (see https://github.com/digitalbitbox and https://shiftcrypto.ch/)

The great folks at Shiftcrypto gave us HSMs to check if we could use them as server HSMs. This is the first Java Library to their HSM.

The CLI executables can be found here: https://digitalbitbox.com/download/client/. 

The API description for the Bitbox is here: https://shiftcrypto.ch/api

Tested with Bitbox firmware version 3 or newer.

To test the HSM, run the JUnit test. Adapt the path of BITBOX_CLI_LOCATION to where you installed the Bitbox CLI command and set its 
password to HSM_PASSWORD variable.
Also, set the scriptLocation parameter of the BitboxClient constructor in case you are running on the Linux. Once build (at least validate) script for could be found in the target folder. 
