# TFM
## Development of Mechanisms for Application Handover in Edge Computing Environments using Software-Defined Networks

This is the GitHub repository for my Master final project. 

**REQUIRED SETUP**

To run this project correctly, please, follow the instructions to install and configure the environment:
- [VNX lab Virtual Machine](https://idefix.dit.upm.es/download/vnx/vnx-vm/VNXLAB2021-v2.ova)
- [ONOS Controller](https://idefix.dit.upm.es/download/vnx/vnx-vm/VNXLAB2021-v2.ova)

**START/STOP SCENARIO**

To start the scenario, [in the Virtual Machine](https://idefix.dit.upm.es/download/vnx/vnx-vm/VNXLAB2021-v2.ova), just run the command:

`sudo vnx -f TFM-FINAL -t`

This will bring the virtual infrastructure up. To check that the scenario has connectivity:

`sudo vnx -f TFM-FINAL --execute pingall`

I recommend to execute this command to see the complete network diagram:

`sudo vnx -f TFM-FINAL -v --show-map`

Now, to install and configure Apache Traffic Server on the Proxy host:

`sudo vnx -f TFM-FINAL --execute ats`

If you run into any DNS resolution problem, just bring the scenario down with the following command and start over:

`sudo vnx -f TFM-FINAL -P`

