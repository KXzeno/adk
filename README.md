# Architectural Decision Knowledgebase

Personal planning and notetaking formatting guide made in LaTeX

## Overview
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
<!-- 
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
## Quick Start :: LaTeX users
1. Go to the projects dir
2. Create a tex file
3. Declare the preamble and template `\usepackage{preamble}`, `\usepackage{adkore}`
4. Fill in fields given by template partial, see `sample.tex` in `...\projects\`

## Installation

## Tech Reqs
[MiKTeX](https://www.bing.com/ck/a?!&&p=83d00a9dd73fd717JmltdHM9MTcwNzY5NjAwMCZpZ3VpZD0xZWNiZjMyYy1jMGNmLTY5YjktMGY1OS1lMDlkYzE1ZDY4MGQmaW5zaWQ9NTUxOA&ptn=3&ver=2&hsh=3&fclid=1ecbf32c-c0cf-69b9-0f59-e09dc15d680d&psq=miktex&u=a1aHR0cHM6Ly9taWt0ZXgub3JnL2Rvd25sb2Fk&ntb=1)

[TeXstudio](https://github.com/texstudio-org/texstudio)

## Docs
### Commands
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
### Environments

##### Hitlist, as of 04-10-2024:
- [ ] Add decision matrix as core project record
