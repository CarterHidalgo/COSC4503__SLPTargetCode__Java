.data
newline: .asciiz "\n"
a: .word 0
b: .word 0

.text
main:
li $t0, 5
li $t1, 3
add $t2, $t0, $t1
sw $t2, a

lw $t3, a
li $v0, 1
move $a0, $t3
syscall
lw $t4, a
li $t5, 1
sub $t6, $t4, $t5
li $v0, 1
move $a0, $t6
syscall
li $v0, 4
la $a0, newline
syscall

li $t7, 10
lw $t8, a
mul $t9, $t7, $t8
sw $t9, b

lw $t2, b
li $v0, 1
move $a0, $t2
syscall
li $v0, 4
la $a0, newline
syscall

li $v0, 10
syscall
