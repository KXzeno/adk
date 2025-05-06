<h2 align="center"> Architectural Decision Knowledgebase </h2>
<p align="center"><em>Personal decision record constructor using <a rel="noreferrer" href="https://www.latex-project.org/">LaTeX</a></em></p>

---

<!--
# Overview
```markdown
DKR
└── ADK
├── assets
├── projects
├── firstProject
│   ├── fp.adr1
│   ├── fp.adr2
│   └── fp.log
├── secondProject
│   ├── sp.adr1
│   ├── sp.adr2
│   └── sp.log
├── locallatexmf 
│   └── tex
├── ADA └── latex
└── ADRe    ├── mystuff
├── mypackages.sty
└── templates
```
  <h3 style="margin-bottom:0 margin-top:0">Parlance</h3>
  <h4 style="margin-bottom:0 margin-top:0">DKR</h4>
  <p style="margin-bottom:0">The Dynamic Knowledge Repository;</p>
  <h4 style="margin-bottom:0 margin-top:0">ADK</h4>
  <p style="margin-bottom:0">The Architectural Dynamic Knowledgebase;</p>
  <h4 style="margin-bottom:0 margin-top:0">assets</h4>
  <p style="margin-bottom:0">Location of where static assets such as images are stored</p>
  <h4 style="margin-bottom:0 margin-top:0">locallatexmf</h4>
  <p style="margin-bottom:0">TDS compliance</p>
  <h4 style="margin-bottom:0 margin-top:0">Projects</h4>
  <p style="margin-bottom:0">Universal;</p>
  <h4 style="margin-bottom:0 margin-top:0"></h4>
  <p style="margin-bottom:0">Record syntax;</p>
  <h4 style="margin-bottom:0 margin-top:0">ADR</h4>
  <p style="margin-bottom:0">Architectural Decision Record;</p>
  <h4 style="margin-bottom:0 margin-top:0">Styles</h4>
  <p style="margin-bottom:0">Styles;</p>
  <h4 style="margin-bottom:0 margin-top:0">[AD]L</h4>
  <p style="margin-bottom:0">Architectural Decision Log;</p>
  <h4 style="margin-bottom:0 margin-top:0">ADA</h4>
  <p style="margin-bottom:0">Architectural Decision Archive;</p>
  <h4 style="margin-bottom:0 margin-top:0">ADRe</h4>
  <p style="margin-bottom:0">Architectural Decision Resources;</p>
-->
## Nomenclature::Modules
<h4><em>Enclave Modules</em></h4>
Keep in mind the act of surveying precedes a record undertaking. This module accounts for projects that demand high costs, and the technical debt projections accrued are alleviated by focusing on approaching goals with decision abstractions which are passed to context modules.
<h4><em>Context Modules</em></h4>
To avoid the prospects of enclave modules from being volatile, their decision abstractions are extended toward context modules for fulfillment. This capitalizes the benefits of <a rel="noreferrer" href="https://en.wikipedia.org/wiki/Separation_of_concerns">separation of concerns</a> and the <a rel="noreferrer" href="https://en.wikipedia.org/wiki/Single-responsibility_principle">single responsibility principle</a>. Due to potential nested layers of abstractions, context modules may delegate responsibilities toward other non-enclave modules.
<h4><em>Terminal (Clastic) Modules</em></h4>
By default, every module besides the enclave module are clastic, modules that do not fracture the enterprise's direction if they were to be scrapped. Only when they require to delegate certain contexts do they become context modules. This mirrors the salience of pruning; synaptic pruning, population pruning, horticultural pruning—where these intersect is the ability to preserve the core from its unnaturalized components. 

## Mental Model
![dynamic-knowledgebase](https://github.com/KXzeno/adk/blob/master/assets/adk-mental-model.png)

## Installation
<em>Soon...</em>

## Quick Start :: LaTeX users
1. Go to the projects dir
2. Create a tex file
3. Declare the preamble and template `\usepackage{preamble}`, `\usepackage{adkore}`
4. Fill in fields given by template partial, see `sample.tex` in `...\projects\`

## Tech Reqs
[MiKTeX](https://miktex.org/download) - TeX distribution

[TeXstudio](https://github.com/texstudio-org/texstudio) - Integrated Development Environment for LaTeX

#### Commands
+ `\version[Record #]{Version Scope}`
- **Record #**: ADR Identifier
- **Version Scope**: Initial Version (Encountered) -- Final Version (Finalized)
+ `\Frontmatter{Type}{Status}{Context}`
- **Type**: Change represented
- **Status**: Current focus on change
- **Context**: Reason for change
+ `\strategy{Core Decision}{Prospects}{Decision Parameters}{0TD Concerns}`
- **Core Decision**: Succinct definition of change approach
- **Prospects**: Expectations for change or what we expect to see
- **Decision Parameters**: Options for change or what we expect to use
- **0TD Concerns**: Potential and ejected concerns for change or what we expect to avoid
+ `\deploy{Decision Process}{Results and Conflicts}`
- **Decision Process**: Systemize parameters, choices made and accompanied thought process
- **Results and Conflicts**: Consequences of parameters, the solved and unsolved
+ `\report{Insight Report}`
- **Insight Report**: Collection/Knowledge experience throughout decision execution
<!-- #### Environments -->

##### Hitlist, as of 12-05-2024:
- [ ] Add decision matrix as core project record
- [ ] Create an intuitive flow|node-chart command
- [ ] Design a UID generator for relational mapping of local enclave modules
- [ ] Reduce operational overhead for consumers
  - With ID generators, allow users to conveniently connect non enclave modules on adr builds
- [ ] Redesign structural logic for better traceability and dynamic frontload
  - Elucidate initial frontload and mid-project adjustments/discoveries
  - Must feel as a living document with clear demarcation of it's lifecycle/timeline
- [ ] Document JavaFX installation for those desiring manual entry-point invocation
