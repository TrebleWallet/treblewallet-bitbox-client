# treblewallet-bitbox-client
Java client library to connect to the Bitbox USB HSM (see https://github.com/digitalbitbox and https://shiftcrypto.ch/)

The great folks at Shiftcrypto gave us HSMs to check if we could use them as server HSMs. This is the first Java Library to their HSM.

The CLI executables can be found here: https://digitalbitbox.com/download/client/. 

The API description for the Bitbox is here: https://shiftcrypto.ch/api

ATTENTION: To make the adapter work, you need to add the java compiler option -parameters. This is to allow an annotation free interface description.
