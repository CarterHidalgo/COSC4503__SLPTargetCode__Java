.data
newline: .asciiz "\n"
a: .word 0
b: .word 0
c: .word 0
e: .word 0
f: .word 0
d: .word 0

.text
main:
li $t0, 3
sw $t0, a

lw $t1, a
li $t2, 4
mul $t3, $t1, $t2
sw $t3, b

lw $t4, b
li $v0, 1
move $a0, $t4
syscall
li $v0, 4
la $a0, newline
syscall

lw $t5, b
lw $t6, a
add $t7, $t5, $t6
sw $t7, c

li $t8, 5
sw $t8, e
lw $t9, e
li $v0, 1
move $a0, $t9
syscall
li $v0, 4
la $a0, newline
syscall

li $t0, 6
li $v0, 1
move $a0, $t0
syscall
lw $t1, e
li $t2, 7
div $t3, $t1, $t2
sw $t3, f
lw $t4, e
lw $t5, f
sub $t6, $t4, $t5
li $v0, 1
move $a0, $t6
syscall
li $v0, 4
la $a0, newline
syscall

li $t7, 8
sw $t7, d
li $v0, 10
syscall
