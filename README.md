# TFM
## Development of Mechanisms for Application Handover in Edge Computing Environments using Software-Defined Networks

This is the GitHub repository for my Master final project. 

**START SCENARIO**

To start the scenario, just run the command:

`sudo vnx -f TFM-FINAL -t`

This will bring the virtual infrastructure up. To check that the scenario has connectivity:

`sudo vnx -f TFM-FINAL --execute pingall`

I recommend to execute this command to see the complete network diagram:

`sudo vnx -f TFM-FINAL -v --show-map`

Now, to install and configure Apache Traffic Server on the Proxy host:

`sudo vnx -f TFM-FINAL --execute ats`

If you run into any DNS resolution problem, just bring the scenario down with the following command and start over:

`sudo vnx -f TFM-FINAL -P`

